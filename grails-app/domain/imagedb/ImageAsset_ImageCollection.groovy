package imagedb

import org.apache.commons.lang.builder.HashCodeBuilder

class ImageAsset_ImageCollection implements Serializable {

    ImageAsset image
    ImageCollection imageCollection

    static constraints = {

    }

    static mapping = {
        id composite: ['imageCollection', 'image']
        version false
    }

    static ImageAsset_ImageCollection get(String imgId, String colId) {
        where {
            image == ImageAsset.load(imgId) &&
            imageCollection == ImageCollection.load(colId)
        }.find()
    }

    static ImageAsset_ImageCollection create(ImageAsset img, ImageCollection col, boolean flush = false) {
        new ImageAsset_ImageCollection(image: img, imageCollection: col)
            .save(flush: flush, insert: true)
    }

    static boolean remove(ImageAsset img, ImageCollection col) {
        where {
            image == ImageAsset.load(img.id) &&
            imageCollection == ImageCollection.load(col.id)
        }.deleteAll()
    }

    static boolean removeAll(ImageAsset img) {
        where { image == ImageAsset.load(img.id) }.deleteAll()
    }

    static boolean removeAll(ImageCollection col) {
        where { imageCollection == ImageCollection.load(col.id) }.deleteAll()
    }

    boolean equals(other) {
        other instanceof ImageAsset_ImageCollection &&
        other.image?.id == imageAsset?.id &&
        other.imageCollection?.id == imageCollection?.id
    }

    int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder()
        if (image)           builder.append(image.id)
        if (imageCollection) builder.append(imageCollection.id)
        builder.toHashCode()
    }
}
