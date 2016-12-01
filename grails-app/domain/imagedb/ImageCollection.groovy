package imagedb

class ImageCollection {

    String name

    static constraints = {
        name blank: false, unique: true
    }

    Set<ImageAsset> getImages() {
        ImageAsset_ImageCollection.findAllByImageCollection(this)
            .collect { it.imageAsset } as Set
    }
}
