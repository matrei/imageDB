package imagedb

class Tag {

    String locale
    String name

    static constraints = {
        name unique: 'locale'
    }

    static searchable = {
        root false
        only = ['name']
    }

    static mapping = {
        table 'tags'
    }

    Set<ImageAsset> getImages() {
        ImageAsset
    }
}
