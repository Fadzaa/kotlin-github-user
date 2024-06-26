package com.example.github_api.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.github_api.R
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.databinding.ActivityDetailUserBinding
import com.example.github_api.view.adapter.FollowPagerAdapter
import com.example.github_api.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var username: String

    private val detailViewModel: DetailViewModel by viewModel { parametersOf(username) }
    companion object{
        const val EXTRA_USER =  "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user  = intent.getSerializableExtra(EXTRA_USER) as? DetailUserResponse

        if (user != null) {
            binding.progressBarHeading?.visibility = View.VISIBLE
            username = user.login
            detailViewModel.setUser(user)
        }

        detailViewModel.user.observe(this) {
            setUser(it)
            setViewPager(it)
            binding.progressBarHeading?.visibility = View.GONE
        }

        with(binding) {
            ivArrowBack.setOnClickListener {
                finish()
            }

            tvPublicRepo.setOnClickListener {
                navigateToRepository()
            }

            ivArrowGreen.setOnClickListener {
                navigateToRepository()
            }
        }

    }

    private fun setUser(user: DetailUserResponse) {
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.civProfileUser)

        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
        binding.tvCompany.text = user.company
        binding.tvLocation.text = user.location
        binding.tvBio.text = user.bio
        binding.tvFollowers.text = getString(R.string.followers, user.followers)
        binding.tvFollowings.text = getString(R.string.followings, user.following)
        binding.tvPublicRepo.text = getString(R.string.public_repo, user.publicRepos)
    }

    private fun setViewPager(user: DetailUserResponse) {
        val followPagerAdapter = FollowPagerAdapter(this)
        binding.viewPager.adapter = followPagerAdapter

        val followers = resources.getString(R.string.followers, user.followers)
        val followings = resources.getString(R.string.followings, user.following)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> followers
                else -> followings
            }
        }.attach()
    }

    private fun navigateToRepository() {
        val intent = Intent(this, RepositoryActivity::class.java)
        intent.putExtra(RepositoryActivity.EXTRA_USERNAME, username)
        startActivity(intent)
    }
}