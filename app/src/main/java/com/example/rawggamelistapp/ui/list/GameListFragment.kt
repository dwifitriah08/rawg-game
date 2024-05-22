import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.rawggamelistapp.data.database.AppDatabase
import com.example.rawggamelistapp.data.database.FavoriteGameDao
import com.example.rawggamelistapp.data.model.Game
import com.example.rawggamelistapp.data.remote.RawgApiService
import com.example.rawggamelistapp.databinding.FragmentGameListBinding
import com.example.rawggamelistapp.network.RetrofitClient
import com.example.rawggamelistapp.ui.detail.GameDetailActivity
import com.example.rawggamelistapp.ui.list.GameListViewModel
import com.example.rawggamelistapp.ui.list.GameListViewModelFactory
import com.example.rawggamelistapp.ui.list.adapter.GameListAdapter

class GameListFragment : Fragment(), GameListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentGameListBinding
    private lateinit var gameListAdapter: GameListAdapter
    private lateinit var favoriteGameDao: FavoriteGameDao
    private val apiService by lazy { RetrofitClient.instance.create(RawgApiService::class.java) }
    private val viewModel: GameListViewModel by viewModels { GameListViewModelFactory(apiService, favoriteGameDao) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app-database")
            .build()
        favoriteGameDao = db.favoriteGameDao()
        setupRecyclerView()
        observeViewModel()
        viewModel.getGames()
    }

    private fun setupRecyclerView() {
        gameListAdapter = GameListAdapter(emptyList(), this)
        binding.recyclerView.apply {
            adapter = gameListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.games.observe(viewLifecycleOwner, Observer { games ->
            games?.let {
                gameListAdapter.submitList(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                // Handle error
            }
        })
    }

    override fun onItemClick(game: Game) {
        redirectToGameDetailActivity(
            game.name,
            game.background_image,
            game.released,
            game.genres.joinToString(", ") { it.name },
            game.id
        )
    }

    companion object {
        fun newInstance() = GameListFragment()
    }

    private fun redirectToGameDetailActivity(
        title: String,
        image: String,
        release: String,
        genre: String,
        id: Int
    ) {
        val intent = Intent(requireContext(), GameDetailActivity::class.java).apply {
            putExtra(GameDetailActivity.EXTRA_TITLE, title)
            putExtra(GameDetailActivity.EXTRA_IMAGE, image)
            putExtra(GameDetailActivity.EXTRA_RELEASE, release)
            putExtra(GameDetailActivity.EXTRA_GENRE, genre)
            putExtra(GameDetailActivity.EXTRA_GAME_ID, id)
        }
        requireContext().startActivity(intent)
    }
}
