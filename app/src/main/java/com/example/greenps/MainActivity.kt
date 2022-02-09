package com.example.greenps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.greenps.View.Login
import com.example.greenps.View.MapsFire
import com.example.greenps.View.Perfil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap
import com.google.firebase.database.DataSnapshot
import com.example.greenps.databinding.ActivityMainBinding
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val mDatabase = Firebase.database
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding
    var tmpRealtimeMarker: ArrayList<Marker> = ArrayList<Marker>()
    var realtimeMarker: ArrayList<Marker> = ArrayList<Marker>()

    enum class ProviderType{
        BASIC
    }

    private lateinit var map:GoogleMap

    companion object{
        const val REQUEST_CODE_LOCATION = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createFragment()

        val bundle = intent.extras
        val nombre = bundle?.getString("nombre")
        val email = bundle?.getString("email")
        val bio = bundle?.getString("bio")
        val password = bundle?.getString("password")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mDatabase
        database = Firebase.database.reference

        setup(nombre?: "",email?: "",bio?: "",password?: "")

    }

    private fun setup(nombre:String,email:String,bio:String,password:String,){
        binding.menubtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val testIntent = Intent(this, Login::class.java).apply {
            }
            startActivity(testIntent)
        }

        binding.comments.setOnClickListener(){
            val intento = Intent(this,Comentarios::class.java).apply {
            }
            startActivity(intento)
        }

        binding.perfile.setOnClickListener(){
            val intent = Intent(this, Perfil::class.java).apply {
                putExtra("nombre", nombre)
                putExtra("email", email)
                putExtra("bio", bio)
                putExtra("password", password)
            }
            startActivity(intent)
        }

        binding.mapacenter.setOnClickListener(){
            Toast.makeText(this, "Ya estas en Mapa", Toast.LENGTH_SHORT).show()
        }

        binding.blog.setOnClickListener(){
            subirLatLonFirebase()
            Toast.makeText(this, "Funcion por implementar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        map= googleMap

        val markListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (marker in realtimeMarker) {
                    marker.remove()
                }

                for (snapshot in dataSnapshot.children) {
                    val mp = snapshot.getValue(MapsFire::class.java)
                    val latitud: Double? = mp?.getLatitud()
                    val longitud: Double? = mp?.getLongitud()

                    val markerOptions = MarkerOptions()
                    markerOptions.position(LatLng(latitud!!, longitud!!))
                    tmpRealtimeMarker.add(map.addMarker(markerOptions)!!)
                }
                realtimeMarker.clear()
                realtimeMarker.addAll(tmpRealtimeMarker)
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.child("usuarios").addValueEventListener(markListener)

        createMarker()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
    }

    private fun createMarker() {
        val coordinates = LatLng(4.657516, -74.094293)
        val marker = MarkerOptions().position(coordinates).title("Parque Central Simón Bolívar")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 12f),
            4000,
            null
        )

    }

    private fun isLocationPermissionGranted()= ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    private fun enableLocation() {
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = true
        }else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission (){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                map.isMyLocationEnabled = true

            } else {
                Toast.makeText(this, "Para activar la localizacion ve a ajustes y acepta los permisos ", Toast.LENGTH_SHORT).show()

            }
            else -> {}
        }



    }

    private  fun subirLatLonFirebase() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                if(location !=null) {
                    Log.e("Latitud: ", (+location.latitude).toString()+" Longitud: " + location.longitude);

                    val latlang:HashMap<String,Double> = HashMap<String,Double>()

                    latlang.put("latitud", location.latitude)
                    latlang.put("longitud", location.longitude)

                    database.child("usuarios").push().setValue(latlang)
                }
            }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if (!isLocationPermissionGranted()){
            map.isMyLocationEnabled == false
            Toast.makeText(this, "Para activar la localizacion ve a ajustes y acepta los permisos ", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Estas en ${p0.latitude} , ${p0.longitude} ", Toast.LENGTH_SHORT).show()

    }
}