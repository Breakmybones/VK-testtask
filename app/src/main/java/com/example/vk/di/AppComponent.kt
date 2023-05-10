package com.example.vk.di

import android.content.Context
import com.example.vk.presentation.fragment.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [FileModule::class])
@Singleton
interface AppComponent {

    fun injectHomeFragment(homeFragment: HomeFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}