package com.location.simplelocation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.demo.location.data_layer.source.GoogleLocationDataSource
import com.location.mylibrary.location.app_layer.TrackViewModel
import com.location.mylibrary.location.data_layer.LocationRepositoryImpl
import com.location.mylibrary.location.domain_layer.GetLocationUseCase
import com.location.simplelocation.ui.theme.SimpleLocationTheme


class MainActivity : ComponentActivity() {

	private val googleLocationDataSource by lazy { GoogleLocationDataSource(this) }
	private val locationRepositoryImpl by lazy { LocationRepositoryImpl(googleLocationDataSource) }
	private val getLocationUseCase by lazy { GetLocationUseCase(locationRepositoryImpl) }

	private val mTrackViewModel by lazy { TrackViewModel(getLocationUseCase) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val pm: PackageManager = packageManager
		val accessFineLocation =
			pm.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, packageName)
		val accessCoarseLocation =
			pm.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, packageName)
		if (accessFineLocation != PackageManager.PERMISSION_GRANTED && accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
			Toast.makeText(this, "Please grant location permission", Toast.LENGTH_LONG).show()
		} else {
			mTrackViewModel.onLocationPermissionGranted()
		}

		setContent {
			SimpleLocationTheme {
				val state by mTrackViewModel.locationModel.collectAsState()
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {

					Greeting("${state.latitude},${state.longitude}")
				}
			}
		}
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Your Location is : $name",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	SimpleLocationTheme {
		Greeting("Android")
	}
}