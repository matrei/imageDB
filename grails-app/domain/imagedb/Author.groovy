package imagedb

class Author {

    String name

    static constraints = {
        name nullable: false, blank: false
    }

    List<ImageAsset> getImages() {
        ImageAsset.findAllByAuthor(this)
    }

    String getDisplayString() {
        name
    }

    static searchable = {
        only = ['name']
    }
}
