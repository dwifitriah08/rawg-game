import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.rawggamelistapp.data.GameRepository
import com.example.rawggamelistapp.data.database.AppDatabase
import com.example.rawggamelistapp.data.database.FavoriteGameDao
import com.example.rawggamelistapp.data.model.FavoriteGame
import com.example.rawggamelistapp.data.remote.RawgApiService
import com.example.rawggamelistapp.databinding.FragmentFavoriteGameListBinding
import com.example.rawggamelistapp.network.RetrofitClient
import com.example.rawggamelistapp.ui.listfav.favAdapter.FavoriteGameDeleteListener
import com.example.rawggamelistapp.ui.listfav.favAdapter.FavoriteGamesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteGamesFragment : Fragment(), FavoriteGameDeleteListener {

    private lateinit var binding: FragmentFavoriteGameListBinding
    private lateinit var favoriteGamesAdapter: FavoriteGamesAdapter
    private lateinit var favoriteGameDao: FavoriteGameDao
    private lateinit var repository: GameRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteGameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database")
            .build()
        favoriteGameDao = db.favoriteGameDao()
        val apiService = RetrofitClient.instance.create(RawgApiService::class.java)
        repository = GameRepository(apiService, favoriteGameDao)

        setupRecyclerView()
        loadFavoriteGames()
    }

    private fun setupRecyclerView() {
        favoriteGamesAdapter = FavoriteGamesAdapter(this)
        binding.recyclerView.adapter = favoriteGamesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadFavoriteGames() {
        GlobalScope.launch(Dispatchers.Main) {
            val favoriteGames = withContext(Dispatchers.IO) {
                repository.getAllFavoriteGames()
            }
            favoriteGamesAdapter.submitList(favoriteGames)
        }
    }

    override fun onDeleteClicked(favoriteGame: FavoriteGame) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                repository.deleteFromDBFavorites(favoriteGame.id)
            }
            loadFavoriteGames()
        }
    }

    companion object {
        fun newInstance() = FavoriteGamesFragment()
    }
}
