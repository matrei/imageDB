package imagedb

class Tag {

    String locale
    String name

    static constraints = {
        name unique: 'locale'
    }

    static searchable = {
        only = ['name']
    }

    static mapping = {
        table 'tags'
    }

    void setName(String name) { this.name = name.toLowerCase() }

    List<ImageAsset> getImages() {
        Tag _this = this;
        if(!this.locale) {
            // Tag instance from ElasticSearch
            _this = load(this.id)
        }
        ImageAsset_Tag.findAllByTag(_this)
            .collect { it.image }
    }
}
