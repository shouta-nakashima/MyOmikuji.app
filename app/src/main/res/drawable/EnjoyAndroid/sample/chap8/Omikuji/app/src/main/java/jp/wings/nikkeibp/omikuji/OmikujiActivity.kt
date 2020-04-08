package jp.wings.nikkeibp.omikuji

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fortune.*
import kotlinx.android.synthetic.main.omikuji.*

class OmikujiActivity : AppCompatActivity(), SensorEventListener {

    lateinit var vibrator: Vibrator
    lateinit var manager: SensorManager

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (omikujiBox.chkShake(event)) {
            if (omikujiNumber < 0) {
                omikujiBox.shake()
            }
        }
/*
        val value = event?.values?.get(0)
        if (value != null && 10 < value) {
            val toast = Toast.makeText(this, "加速度：${value}", Toast.LENGTH_LONG)
            toast.show()
        }
*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
/*
        val toast = Toast.makeText(this, item?.title, Toast.LENGTH_LONG)
        toast.show()
*/
        if (item?.itemId == R.id.item1) {
            val intent = Intent(this, OmikujiPreferenceActivity::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    // おみくじ棚の配列
    val omikujiShelf = Array<OmikujiParts>(20)
        { OmikujiParts(R.drawable.result2, R.string.contents1) }

    // おみくじ番号保管用
    var omikujiNumber = -1

    val omikujiBox = OmikujiBox()

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        val sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.omikuji)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val value = pref.getBoolean("button", false)
        button.visibility = if (value) View.VISIBLE else View.INVISIBLE

        omikujiBox.omikujiView = imageView

        // おみくじ棚の準備
        omikujiShelf[0].drawID = R.drawable.result1
        omikujiShelf[0].fortuneID = R.string.contents2
        omikujiShelf[1].drawID = R.drawable.result3
        omikujiShelf[1].fortuneID = R.string.contents9
        omikujiShelf[2].fortuneID = R.string.contents3
        omikujiShelf[3].fortuneID = R.string.contents4
        omikujiShelf[4].fortuneID = R.string.contents5
        omikujiShelf[5].fortuneID = R.string.contents6

/*
        // くじ番号の取得
        val rnd = Random()
        val number = rnd.nextInt(20)

        // おみくじ棚の準備
        val omikujiShelf = Array<String>(20, {"吉"})
        omikujiShelf[0] = "大吉"
        omikujiShelf[19] = "凶"

        // おみくじ棚から取得
        val str = omikujiShelf[number]
        hello_view.text = str
*/
    }
    fun onButtonClick(v:View) {

        omikujiBox.shake()
/*
        val translate = TranslateAnimation(0f, 0f, 0f, -200f)
        translate.repeatMode = Animation.REVERSE
        translate.repeatCount = 5
        translate.duration = 100

        val rotate = RotateAnimation(0f, -36f, imageView.width/2f, imageView.height/2f)
        rotate.duration = 200
        val set = AnimationSet(true)
        set.addAnimation(rotate)
        set.addAnimation(translate)

        imageView.startAnimation(set)
*/
        // imageView.setImageResource(R.drawable.result1)

    }

    fun drawResult() {
        // おみくじ番号を取得する
        omikujiNumber = omikujiBox.number

        // おみくじ棚の配列から、omikujiPartsを取得する
        val op = omikujiShelf[omikujiNumber]

        // レイアウトを運勢表示に変更する
        setContentView(R.layout.fortune)

        // 画像とテキストを変更する
        imageView2.setImageResource(op.drawID)
        textView.setText(op.fortuneID)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){
            if (omikujiNumber < 0 && omikujiBox.finish) {
                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                if (pref.getBoolean("vibration", false)) {
                    vibrator.vibrate(50L)
                }
                drawResult()
            }
        }
        return super.onTouchEvent(event)
    }
}



