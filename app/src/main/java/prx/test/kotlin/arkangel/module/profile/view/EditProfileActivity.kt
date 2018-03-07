package prx.test.kotlin.arkangel.module.profile.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.app_bar_edit_profile.*
import prx.test.kotlin.arkangel.R
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.content_edit_profile.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import prx.test.kotlin.arkangel.R.id.*
import prx.test.kotlin.arkangel.module.profile.model.User
import java.util.stream.Collectors.toList
import java.util.stream.Collectors.toMap


class EditProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var profileImageUrl:String
    var uriProfileImage: Uri? = null
    private var mStorageRef: StorageReference? = null
    private val RESULT_LOAD_IMAGE = 101

    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(toolbar)


        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.getCurrentUser()

        if (user != null) {
            if(user.getPhotoUrl()!=null)
            profileImageUrl = user!!.getPhotoUrl()!!.toString()
            loadUserInformation(user)

        }
        file_upload1.setOnClickListener {
            showImageChooser()
        }

        saveBtn1.setOnClickListener {
            save()
        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.edit_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadUserInformation(user:FirebaseUser) {

        val userId = mAuth.currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users/" + userId)


        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val profile = dataSnapshot.getValue(User::class.java)

                first_name1.setText(profile?.firstName)
                last_name1.setText(profile?.lastName)
//                emailUpdate.setText(user?.email)


            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user!!.getPhotoUrl()!!.toString())
                        .into(picture)
        }
//            if (user!!.getDisplayName() != null) {
//                last_name1.setText(user!!.getDisplayName())
//            }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data && data.data!=null) {
            uriProfileImage = data.data
            val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uriProfileImage)
            picture.setImageBitmap(bitmap)
            firebaseStorage()

        }
    }


    private fun showImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), RESULT_LOAD_IMAGE)
    }

    private fun firebaseStorage(){

        val prog = ProgressDialog(this@EditProfileActivity)
        prog.setMessage("Uploading photo")
        prog.show()

        mStorageRef =  FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage!=null && mStorageRef!=null){

            mStorageRef!!.putFile(uriProfileImage!!)
                    .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        prog.hide()
                        profileImageUrl = taskSnapshot.downloadUrl.toString()
                    })
                    .addOnFailureListener(OnFailureListener {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show();                    // ...
                    })
        }

    }


    fun save() {

        val prog = ProgressDialog(this@EditProfileActivity)
        prog.setMessage("Updating profile")
        prog.show()

        var database = FirebaseDatabase.getInstance();

        val userId = mAuth.currentUser?.uid;
        val firstName1 = first_name1.text.toString()
        val lastName1 = last_name1.text.toString()

        val user = User()
        user.firstName = firstName1
        user.lastName = lastName1

        user.email = mAuth.currentUser?.email


        database.getReference("users/" + userId).setValue(user)

        var firebaseUser: FirebaseUser? = mAuth.currentUser

        if (firebaseUser != null && profileImageUrl != null) {

            val profile = UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build()

            firebaseUser.updateProfile(profile)
                    .addOnCompleteListener(OnCompleteListener {
                        prog.hide()
                        if (it.isSuccessful()) {
                            Toast.makeText(applicationContext, "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    })

        }
    }


    @Throws(JSONException::class)
    fun jsonToMap(json: JSONObject): Map<String, Any> {
        var retMap: Map<String, Any> = HashMap()

        if (json !== JSONObject.NULL) {
            retMap = toMap(json)
        }
        return retMap
    }

    @Throws(JSONException::class)
    fun toMap(`object`: JSONObject): Map<String, Any> {
        val map = HashMap<String, Any>()

        val keysItr = `object`.keys()
        while (keysItr.hasNext()) {
            val key = keysItr.next()
            var value = `object`.get(key)

            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            map[key] = value
        }
        return map
    }

    @Throws(JSONException::class)
    fun toList(array: JSONArray): List<Any> {
        val list = ArrayList<Any>()
        for (i in 0 until array.length()) {
            var value = array.get(i)
            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            list.add(value)
        }
        return list
    }

}


//        }
//        var displayName :String = first_name.text.toString() +" "+last_name.text.toString()
//        var user : FirebaseUser? = mAuth.currentUser
//
//        if (user != null && profileImageUrl != null) {
//            val profile = UserProfileChangeRequest.Builder()
//                    .setDisplayName(displayName)
//                    .setPhotoUri(Uri.parse(profileImageUrl))
//                    .build()
//
//            user.updateProfile(profile)
//                    .addOnCompleteListener(OnCompleteListener {
//                        prog.hide()
//                        if (it.isSuccessful()) {
//                            Toast.makeText(applicationContext, "Profile Updated", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//
//        }
