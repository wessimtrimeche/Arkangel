package prx.test.kotlin.arkangel.profile.view

import android.app.Activity
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.profile.presenter.ProfilePresenter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.support.annotation.NonNull
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import prx.test.kotlin.arkangel.R.drawable.profile
import java.io.File


class ProfileActivity : AppCompatActivity(), ProfileView {


    lateinit var profileImageUrl:String
    lateinit var mAuth : FirebaseAuth
    var uriProfileImage: Uri? = null
    private var mStorageRef: StorageReference? = null
    private val RESULT_LOAD_IMAGE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val profilePresenter = ProfilePresenter(this)

        mAuth = FirebaseAuth.getInstance()

        file_upload.setOnClickListener {
            showImageChooser()
        }

        saveBtn.setOnClickListener {
            save()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data && data.data!=null) {
            uriProfileImage = data.data
            val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uriProfileImage)
            imageView.setImageBitmap(bitmap)
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

        val prog = ProgressDialog(this@ProfileActivity)
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

    fun save(){

        val prog = ProgressDialog(this@ProfileActivity)
        prog.setMessage("Updating profile")
        prog.show()

        var displayName :String = first_name.text.toString() +" "+last_name.text.toString()
        var user : FirebaseUser? = mAuth.currentUser

        if (user != null && profileImageUrl != null) {
            val profile = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build()

            user.updateProfile(profile)
                    .addOnCompleteListener(OnCompleteListener {
                        prog.hide()
                        if (it.isSuccessful()) {
                            Toast.makeText(applicationContext, "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    })

        }




    }
}
