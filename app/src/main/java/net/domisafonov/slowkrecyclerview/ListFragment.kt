package net.domisafonov.slowkrecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import net.domisafonov.slowkrecyclerview.databinding.FragmentListBinding
import net.domisafonov.slowkrecyclerview.databinding.ItemBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.list.adapter = Adapter()
        binding.list2.adapter = Adapter()
        return binding.root
    }
}

class Adapter : RecyclerView.Adapter<VH>() {

    override fun getItemCount() = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position)
    }
}

class VH(private val binding: ItemBinding) : ViewHolder(binding.root) {

    fun bind(n: Int) {
        binding.text.text = n.toString()
    }
}
