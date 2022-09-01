package Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.saram.testapp.ChatFragment
import com.saram.testapp.FriendFragment
import com.saram.testapp.LocationFragment

//import com.saram.anon.fra.*

class MainViewPager(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LocationFragment()
            1 -> ChatFragment()
            else -> FriendFragment()
        }
    }
}