package jp.wings.nikkeibp.omikuji

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main.*

class OmikujiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        // 文字を表示する
        hello_view.text = "おみくじアプリ"
    }
}
