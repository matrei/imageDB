package imagedb

class BootStrap {

    def init = { servletContext ->
        environments {
            development {
                if(!ImageAsset.count()) createAssets()
            }
        }
    }
    def destroy = {
    }

    private static void createAssets() {

        def collection = new ImageCollection(name: "Bootstrap").save(failOnError: true)
        def author = new Author(name: "John Doe").save(failOnError: true)

        List<ImageAsset> assets = []
        10.times {
            def asset = new ImageAsset(filename: "image${it}.jpg", author: author).save(failOnError: true)
            new Metadata(name: "Camera", value: "Canon", image: asset).save(failOnError: true)
            ImageAsset_ImageCollection.create(asset, collection)
            assets << asset
        }

        Tag tag_noll = Tag.findOrCreateByLocaleAndName('sv', 'noll').save(failOnError: true)
        Tag tag_zero = Tag.findOrCreateByLocaleAndName('en', 'zero').save(failOnError: true)
        Tag tag_nil = Tag.findOrCreateByLocaleAndName('en', 'nil').save(failOnError: true)
        addTranslation(tag_noll, tag_zero, tag_nil)
        addSynonym(tag_nil, tag_zero)

        Tag tag_ett = Tag.findOrCreateByLocaleAndName('sv', 'ett').save(failOnError: true)
        Tag tag_one = Tag.findOrCreateByLocaleAndName('en', 'one').save(failOnError: true)
        addTranslation(tag_ett, tag_one)

        Tag tag_tva = Tag.findOrCreateByLocaleAndName('sv', 'två').save(failOnError: true)
        Tag tag_two = Tag.findOrCreateByLocaleAndName('en', 'two').save(failOnError: true)
        addTranslation(tag_tva, tag_two)

        Tag tag_siffra = Tag.findOrCreateByLocaleAndName('sv', 'siffra').save(failOnError: true)
        Tag tag_digit = Tag.findOrCreateByLocaleAndName('en', 'digit').save(failOnError: true)
        Tag tag_figure = Tag.findOrCreateByLocaleAndName('en', 'figure').save(failOnError: true)
        addTranslation(tag_siffra, tag_digit)
        addSynonym(tag_digit, tag_figure)

        addTagToImage(assets[0], tag_noll)
        addTagToImage(assets[1], tag_ett)
        addTagToImage(assets[2], tag_tva)

        3.times {
            addTagToImage(assets[it], tag_siffra)
        }
    }

    private static void addTagToImage(image, tag) {
        new ImageAsset_Tag(image: image, tag: tag, manuallyAdded: true).save(failOnError: true)
        getRelatedTags(tag).each {
            new ImageAsset_Tag(image: image, tag: it, manuallyAdded: false).save(failOnError: true)
        }
    }

    private static Set<Tag> getRelatedTags(Tag tag) {
        def relatedTags = getAllTags(tag)
        relatedTags.remove(tag)
        relatedTags
    }

    private static Set<Tag> getAllTags(Tag tag, Set<Tag> tags = []) {
        if(!tags.contains(tag)) {
            tags << tag
        }
        tag.translations.each {
            if(!tags.contains(it)) {
                Set<Tag> relatedTags = getAllTags(it, tags)
                relatedTags.each {
                    if(!tags.contains(it)) {
                        tags << it
                    }
                }
            }
        }
        tag.synonyms.each {
            if(!tags.contains(it)) {
                Set<Tag> relatedTags = getAllTags(it, tags)
                relatedTags.each {
                    if(!tags.contains(it)) {
                        tags << it
                    }
                }
            }
        }
        tags
    }

    private static void addTranslation(Tag... tags) {
        for(int i = 0; i < tags.length; i++) {
            for(int j = 0; j < tags.length; j++) {
                if(!tags[i].is(tags[j])) {
                    tags[i].addToTranslations(tags[j])
                    tags[i].save(failOnError: true)
                }
            }
        }
    }

    private static void addSynonym(Tag... tags) {
        for(int i = 0; i < tags.length; i++) {
            for(int j = 0; j < tags.length; j++) {
                if(!tags[i].is(tags[j])) {
                    tags[i].addToSynonyms(tags[j])
                    tags[i].save(failOnError: true)
                }
            }
        }
    }
}
