/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.net.sample.ui.activity

import android.os.Handler
import android.os.Looper
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.drake.engine.base.EngineActivity
import com.drake.net.sample.R
import com.drake.net.sample.databinding.ActivityMainBinding
import com.drake.net.sample.model.UserModel
import com.drake.net.sample.ui.fragment.PullRefreshFragment
import com.drake.statusbar.immersive

/**
 * 以下代码设置导航, 和框架本身无关无需关心, 请查看[com.drake.net.sample.ui.fragment]内的Fragment
 */
class MainActivity : EngineActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
     /*   immersive(binding.toolbar, true)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav)
        binding.toolbar.setupWithNavController(
            navController,
            AppBarConfiguration(binding.drawerNav.menu, binding.drawer)
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.subtitle =
                (destination as FragmentNavigator.Destination).className.substringAfterLast('.')
        }
        binding.drawerNav.setupWithNavController(navController)*/

        val f = PullRefreshFragment()
        addFragment(f, R.id.nav)
        //延迟动作 避开初始化问题
        val h = Handler(Looper.getMainLooper())
        h.postDelayed(Runnable {
            f.getData<UserModel>()
        }, 3000)
    }

    override fun initData() {
    }

    /*override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) binding.drawer.closeDrawers() else super.onBackPressed()
    }*/
    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }
}









