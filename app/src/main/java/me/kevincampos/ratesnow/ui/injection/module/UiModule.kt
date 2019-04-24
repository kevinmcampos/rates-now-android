package me.kevincampos.ratesnow.ui.injection.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.kevincampos.ratesnow.ui.currency.ConversionsActivity

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributesConversionsActivity(): ConversionsActivity
}