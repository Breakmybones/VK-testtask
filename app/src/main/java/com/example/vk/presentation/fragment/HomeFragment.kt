package com.example.vk.presentation.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vk.BuildConfig
import com.example.vk.R
import com.example.vk.data.database.DataBaseRepository
import com.example.vk.data.database.model.FileLocal
import com.example.vk.databinding.FragmentHomeBinding
import com.example.vk.di.App
import com.example.vk.domain.model.FileModel
import com.example.vk.domain.useCase.*
import com.example.vk.presentation.fragment.list.FileAdapter
import com.example.vk.presentation.fragment.viewModel.HomeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home), DialogType.TypeInputListener {

    private var binding: FragmentHomeBinding? = null
    private var adapter: FileAdapter? = null
    private var files = mutableListOf<FileModel>()
    private var changedFiles = mutableListOf<FileModel>()
    lateinit var repository: DataBaseRepository
    val dialog = DialogType()
    private val sortBy = arrayOf("По дате", "По размеру(убывание)", "По размеру(возрастание", "По расширению")

    @Inject
    lateinit var getImagesUseCase: GetImagesUseCase

    @Inject
    lateinit var getVideoUseCase: GetVideoUseCase

    @Inject
    lateinit var getMediaUseCase: GetMediaUseCase

    @Inject
    lateinit var getDocumentsUseCase: GetDocumentsUseCase

    @Inject
    lateinit var getDownloadsUseCase: GetDownloadsUseCase

    @Inject
    lateinit var getImagesFolderUseCase: GetImagesFolderUseCase

    @Inject
    lateinit var getVideoFolderUseCase: GetVideoFolderUseCase

    @Inject
    lateinit var getMediaFolderUseCase: GetMediaFolderUseCase

    @Inject
    lateinit var getDownloadsFolderUseCase: GetDownloadsFolderUseCase

    @Inject
    lateinit var getDocumentsFolderUseCase: GetDocumentsFolderUseCase

    @Inject
    lateinit var getFileHashUseCase: GetFileHashUseCase

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.provideFactory(
            getDocumentsFolderUseCase,
            getDocumentsUseCase,
            getDownloadsFolderUseCase,
            getDownloadsUseCase,
            getImagesFolderUseCase,
            getImagesUseCase,
            getMediaFolderUseCase,
            getMediaUseCase,
            getVideoFolderUseCase,
            getVideoUseCase,
            getFileHashUseCase

        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = DataBaseRepository(requireContext())
    }

    override fun onAttach(context: Context) {
        App.appComponent.injectHomeFragment(this)
        super.onAttach(context)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Timber.tag("HomeFragment").e("Разрешение на чтение EXTERNAL_STORAGE получено")
                } else {
                    Timber.tag("HomeFragment").e("Разрешение на чтение EXTERNAL_STORAGE не получено")
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        adapter = FileAdapter(
            actionNext = { openFile(File(it.path!!)) },
            actionSend = { shareFile(File(it.path!!)) },
            actionOpenFolder = { viewModel.openFolder(it, requireContext()) }
        )
        binding?.rvFiles?.adapter = adapter
        binding?.rvFiles?.layoutManager = LinearLayoutManager(requireContext())

        observeViewModel()

        adapter?.submitList(files)

        //извиняюсь за проверку разрешений в onViewCreated, но надо было инициализовать адаптер(
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {

            viewModel.getAllFiles(requireContext())
            files.sortBy { it.name }
        } else {
            // Разрешение не предоставлено, запрашиваем у пользователя
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_EXTERNAL_STORAGE)
        }

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val arrayAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sortBy)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2) {
                    0 -> {
                        files.sortByDescending { it.date }
                        adapter?.submitList(files)
                        adapter?.notifyDataSetChanged()
                    }
                    1 -> {
                        files = files.sortedBy { it.size } as MutableList<FileModel>
                        adapter?.submitList(files)
                        adapter?.notifyDataSetChanged()
                    }
                    2 -> {
                        files.sortByDescending { it.size }
                        adapter?.submitList(files)
                        adapter?.notifyDataSetChanged()
                    }
                    3 -> {
                        dialog.show(parentFragmentManager, "NameInputDialogFragment")
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Ничего не выбрано", Toast.LENGTH_SHORT).show()
            }

        }

        binding?.run {
            ivChange.setOnClickListener {
                adapter?.submitList(changedFiles)
            }
        }

    }

    private fun openFile(file: File) {
        val uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file)

        val mime = MimeTypeMap.getSingleton()
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        val type = mime.getMimeTypeFromExtension(extension)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, type)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        startActivity(Intent.createChooser(intent, "Открыть файл"))
    }


    private fun shareFile(file: File) {
        // Получаем uri файла с помощью FileProvider
        val uri = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )

        // Создаем новый intent на отправку
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            // Указываем тип данных для отправки
            type = getMimeType(file)
            // Добавляем uri файла в intent
            putExtra(Intent.EXTRA_STREAM, uri)
            // Добавляем флаг, чтобы дать доступ к чтению файла другим приложениям
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        // Запускаем диалоговое окно выбора приложения для отправки файла
        startActivity(Intent.createChooser(sendIntent, "Share file using"))
    }

    private fun getMimeType(file: File): String {
        // Получаем расширение файла
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.absolutePath)
        // Получаем MIME-тип для расширения файла
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "*/*"
    }

    fun getFilesTypes(type: String?) {
        if (type != null) {
            val newFiles = files.filter { it.exception == type }
            adapter?.submitList(newFiles)
        }
    }

    fun addFileToDataBase(file: FileModel) {
        val fileLocal = FileLocal(
            hashCode = file.hashCode,
            name = file.name
        )
        lifecycleScope.launch {
            val hashCode = fileLocal.name?.let { repository.getHashCode(it) }
            if (hashCode.toString() != fileLocal.hashCode) {
                fileLocal.name?.let {
                    fileLocal.hashCode?.let { it1 ->
                        repository.setHashCode(it,
                            it1)
                    }
                }
            } else {
                return@launch
            }
        }
        changedFiles.add(file)
    }

    private fun observeViewModel() {
        with(viewModel) {
            listOfFiles.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                files = it
                for (item in it) {
                    if (!item.isFolder) {
                        addFileToDataBase(item)
                    }
                }
                setListAdapterConfig(it)
            }
            listOfFilesOnFolder.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                files = it
                for (item in it) {
                    if (!item.isFolder) {
                        addFileToDataBase(item)
                    }
                }
                setListAdapterConfig(it)
            }
        }
    }

    private fun setListAdapterConfig(list: MutableList<FileModel>) {
        binding?.rvFiles?.adapter = adapter
        adapter?.submitList(list)
    }



    companion object {
        const val HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG"
        const val TYPE = "TYPE"
        private const val REQUEST_EXTERNAL_STORAGE = 1
        fun newInstance(bundle: Bundle) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }

    override fun onTypeInputted(name: String) {
        getFilesTypes(name)
    }
}