package prx.test.kotlin.arkangel.data

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import prx.test.kotlin.arkangel.module.profile.model.User

/**
 * Created by wessim23 on 3/5/18.
 */

open class DataManager

/*public usrlogin(){
    networkprovider.login()
    }*///rxjava fl onComplete sauvegarde fl cache par ex
{


    fun createUser(mAuth: FirebaseAuth, firstName: String, lastName: String) {

        val user = User()

        var database = FirebaseDatabase.getInstance()
        val firebaseUser = mAuth.currentUser
//        val firstName1 = firstnameEditText.text.toString()
//        val lastName1 = lastnameEditText.text.toString()
//        var displayName = mAuth.currentUser?.displayName

        user.firstName = firstName
        user.lastName = lastName
        var displayName = user.firstName + " " + user.lastName



        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName).build()
        firebaseUser?.updateProfile(profileUpdates)
//        Log.d("testtest",firebaseUser?.displayName)

        val userEmail = mAuth.currentUser?.email
        user.email = userEmail

        val ref: DatabaseReference = database.getReference("users/" + displayName)
        ref.setValue(user)
    }


    fun createUserGoogle(mAuth: FirebaseAuth, account: GoogleSignInAccount) {

        val user = User()

        var database = FirebaseDatabase.getInstance()
        val userId = mAuth.currentUser?.uid;

        user.firstName = account.givenName
        user.lastName = account.familyName

        var displayName = user.firstName + " " + user.lastName
        val userEmail = mAuth.currentUser?.email
        user.email = userEmail

        val ref: DatabaseReference = database.getReference("users/" + displayName)
        ref.setValue(user)
    }

}
