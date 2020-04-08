package jp.wings.nikkeibp.omikuji

import java.util.*

class OmikujiBox {
    val number : Int  // くじ番号（0～19の乱数）
    get() {
        val rnd = Random()
        return rnd.nextInt(20)
    }
}