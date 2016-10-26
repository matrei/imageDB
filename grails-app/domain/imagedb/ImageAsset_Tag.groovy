package imagedb

import org.apache.commons.lang.builder.HashCodeBuilder

class ImageAsset_Tag {

    ImageAsset image
    Tag tag

    boolean equals(other) {
        if(!(other instanceof ImageAsset_Tag)) {
            return false
        }
        other.image?.id == image?.id &&
        other.tag?.id   == tag?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if(imageAsset) builder.append(imageAsset.id)
        if(tag       ) builder.append(tag.id)
        builder.toHashCode()
    }

    static constraints = {
    }
}
