package com.example.saferoute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.saferoute.databinding.ActivityAppGuideBinding
import com.google.android.material.tabs.TabLayoutMediator

data class GuidePage(val iconResId: Int, val description: String)

class AppGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppGuideBinding

    private val guidePages = listOf(
        GuidePage(R.drawable.ic_shield, "Let’s set things up so you’re protected everywhere you go."),
        GuidePage(R.drawable.ic_guide_sos, "Press the SOS button to send an alert to your trusted contacts."),
        GuidePage(R.drawable.ic_guide_location, "Share your live location with your trusted contacts."),
        GuidePage(R.drawable.ic_guide_fake_call, "Simulate a fake call to get out of an uncomfortable situation."),
        GuidePage(R.drawable.ic_guide_contacts, "Manage your trusted contacts and personal information.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = GuidePagerAdapter(guidePages)

        TabLayoutMediator(binding.dotsIndicator, binding.viewPager) { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    binding.backButton.visibility = View.INVISIBLE
                } else {
                    binding.backButton.visibility = View.VISIBLE
                }
                if (position == guidePages.size - 1) {
                    binding.nextButton.text = "Finish"
                } else {
                    binding.nextButton.text = "Next"
                }
            }
        })

        binding.nextButton.setOnClickListener {
            if (binding.viewPager.currentItem == guidePages.size - 1) {
                finish()
            } else {
                binding.viewPager.currentItem++
            }
        }

        binding.backButton.setOnClickListener {
            binding.viewPager.currentItem--
        }
    }

    private class GuidePagerAdapter(private val pages: List<GuidePage>) : RecyclerView.Adapter<GuidePagerAdapter.GuideViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.guide_page_item, parent, false)
            return GuideViewHolder(view)
        }

        override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
            holder.bind(pages[position])
        }

        override fun getItemCount(): Int = pages.size

        class GuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val icon: ImageView = itemView.findViewById(R.id.guide_icon)
            private val description: TextView = itemView.findViewById(R.id.guide_description)

            fun bind(page: GuidePage) {
                icon.setImageResource(page.iconResId)
                description.text = page.description
            }
        }
    }
}