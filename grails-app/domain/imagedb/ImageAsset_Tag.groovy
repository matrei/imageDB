package imagedb

import org.apache.commons.lang.builder.HashCodeBuilder

class ImageAsset_Tag implements Serializable {

    ImageAsset image
    Tag tag
    Boolean manuallyAdded

    static mapping = {
        id composite: ['tag', 'image']
        version false
    }

    static constraints = {
    }

    static ImageAsset_Tag create(ImageAsset image, Tag tag, boolean manuallyAdded, boolean flush = false) {
        new ImageAsset_Tag(imageAsset: image, tag: tag, manuallyAdded: manuallyAdded)
                .save(flush: flush, insert: true)
    }

    static boolean remove(ImageAsset i, Tag t) {
        where {
            image == ImageAsset.load(i.id) &&
            tag == Tag.load(t.id)
        }.deleteAll()
    }

    static boolean removeAll(ImageAsset i) {
        where {
            image == ImageAsset.load(i.id)
        }.deleteAll()
    }

    static boolean removeAll(Tag t) {
        where {
            tag == Tag.load(t.id)
        }.deleteAll()
    }

    boolean equals(other) {
        other instanceof ImageAsset_Tag &&
        other.image?.id == image?.id &&
        other.tag?.id == tag?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if(image) builder.append(image.id)
        if(tag)   builder.append(tag.id)
        builder.toHashCode()
    }
}