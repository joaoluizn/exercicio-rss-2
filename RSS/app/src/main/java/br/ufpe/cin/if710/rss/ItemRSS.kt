package br.ufpe.cin.if710.rss

class ItemRSS(val title: String, val link: String, val pubDate: String, val description: String, val readStatus: Boolean) {

    private var read = false

    override fun toString(): String {
        return title
    }

    fun maskAsRead(){
        if(!this.read) {
            this.read = true
        }
    }

    fun markAsUnread(){
        if(this.read){
            this.read = false
        }
    }

}
