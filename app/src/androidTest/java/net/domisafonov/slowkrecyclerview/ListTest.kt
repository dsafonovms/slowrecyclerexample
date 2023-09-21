package net.domisafonov.slowkrecyclerview

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.common.builders.ViewBuilder
import io.github.kakaocup.kakao.common.matchers.ItemMatcher
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import org.hamcrest.Matcher
import org.junit.Test

class ListTest : TestCase() {

    @Test
    fun test() = before {
        launchFragmentInContainer<ListFragment>(themeResId = R.style.Theme_SlowKrecyclerview)
    }.after {}.run {

        Scr {
            (0 until 5).forEach {
                assertItems(it)
            }
        }
    }
}

object Scr : KScreen<Scr>() {

    override val layoutId = R.layout.fragment_list
    override val viewClass = ListFragment::class.java

    private val list = KRecyclerView(
        builder = { withId(R.id.list) },
        itemTypeBuilder = { itemType(::ListItem) },
    )

    private val list2 = KRecyclerView(
        builder = { withId(R.id.list2) },
        itemTypeBuilder = { itemType(::ListItem) },
    )

    fun assertItems(n: Int) {
        list.childWith<ListItem>(ListItem.matchText(n.toString())).isDisplayed()
        list2.childWith<ListItem>(ListItem.matchText(n.toString())).isDisplayed()
    }

    class ListItem(root: Matcher<View>) : KRecyclerItem<ListItem>(root) {

        companion object {

            fun matchText(str: String): ViewBuilder.() -> Unit = { withDescendant {
                withId(R.id.text)
                withText(str)
                isDisplayed()
            } }
        }
    }
}

inline fun <reified T : KRecyclerItem<*>> KRecyclerView.childWithNoScroll(noinline childMatcher: ViewBuilder.() -> Unit): T {
    val provideItem = itemTypes.getOrElse(T::class) {
        throw IllegalStateException("${T::class.java.simpleName} did not register to KRecyclerView")
    }.provideItem

    return (provideItem(
        ItemMatcher(
            matcher,
            ViewBuilder().apply(childMatcher).getViewMatcher()
        )
    ) as T).also { inRoot { withMatcher(this@childWithNoScroll.root) } }
}
