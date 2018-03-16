package prx.test.kotlin.arkangel.module.childProfile.view

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kelvinapps.rxfirebase.RxFirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_add_child.*
import kotlinx.android.synthetic.main.app_bar_add_child.*
import kotlinx.android.synthetic.main.content_add_child.*
import prx.test.kotlin.arkangel.R
import prx.test.kotlin.arkangel.module.authentication.view.LoginActivity
import prx.test.kotlin.arkangel.module.home.view.MainActivity
import prx.test.kotlin.arkangel.module.profile.model.Child
import prx.test.kotlin.arkangel.module.profile.model.User
import prx.test.kotlin.arkangel.module.profile.view.EditProfileActivity
import java.util.*


class AddChildActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mDateSetListener: DatePickerDialog.OnDateSetListener? = null
    lateinit var mAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_child)
        setSupportActionBar(toolbar)


        mAuth = FirebaseAuth.getInstance()

        firebaseUser = mAuth.getCurrentUser()!!


        val header = nav_view_add_child.getHeaderView(0)
        val userMail : TextView = header.findViewById (R.id.user_email)
        val userName : TextView = header.findViewById (R.id.username)
        val imageView : CircleImageView = header.findViewById (R.id.imageView)




        userMail.setText(firebaseUser.email)
        userName.setText(firebaseUser.displayName)
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(firebaseUser!!.getPhotoUrl()!!.toString())
                    .into(imageView)
        }

        imageView.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))

        })


        calendar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)
                mDateSetListener = object: DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(datePicker: DatePicker, year:Int, month:Int, day:Int) {
                        var year1 = year
                        var month1=month
                        var day1=day
                        month1 = month1 + 1
//                        Log.d("AddChild", "onDateSet: mm/dd/yyy: " + month1 + "/" + day1 + "/" + year1)
                        val date = ""+day1 + "/" + month1 + "/" + year1
                        birthDate.setText(date)

                    }
                }
                val dialog = DatePickerDialog(this@AddChildActivity,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day)
                dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

            }


        })

        val uuid = UUID.randomUUID().toString()
        val code = uuid.substring(0,8)
        parentCode.setText(code)

        val mAuth= FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var displayName = mAuth.currentUser?.displayName

        val ref = FirebaseDatabase.getInstance().getReference()

        saveBtn.setOnClickListener(View.OnClickListener {
            RxFirebaseDatabase.observeSingleValueEvent(ref.child("users/" + displayName), User::class.java)
                    .subscribe({ user ->
                        val child = Child()

                        var childList: MutableList<Child>? = mutableListOf<Child>()

                        child.name=childName.text.toString()
                        child.parentCode=code

                        childList?.add(child)

                        user.childList=childList

//                        var newChild= database.getReference().child(displayName).push().key


                        ref.child("users").child(displayName).child("childsList/"+child.parentCode).setValue(user.childList)

                    })

        })


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_add_child.setNavigationItemSelectedListener(this)
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
        menuInflater.inflate(R.menu.add_child, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.user_profile -> {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.nav_manage -> {
                finish()
                startActivity(Intent(this, EditProfileActivity::class.java))

            }
            R.id.help -> {

            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
