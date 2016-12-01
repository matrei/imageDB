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

        Tag tag_noll = Tag.findOrCreateByLocaleAndName('sv_SE', 'noll').save(failOnError: true)
        Tag tag_ett = Tag.findOrCreateByLocaleAndName('sv_SE', 'ett').save(failOnError: true)
        Tag tag_tva = Tag.findOrCreateByLocaleAndName('sv_SE', 'två').save(failOnError: true)
        Tag tag_siffra = Tag.findOrCreateByLocaleAndName('sv_SE', 'siffra').save(failOnError: true)

        new ImageAsset_Tag(image: assets[0], tag: tag_noll).save(failOnError: true)
        new ImageAsset_Tag(image: assets[1], tag: tag_ett).save(failOnError: true)
        new ImageAsset_Tag(image: assets[2], tag: tag_tva).save(failOnError: true)
        new ImageAsset_Tag(image: assets[3], tag: tag_tva).save(failOnError: true)

        3.times {
            new ImageAsset_Tag(image: assets[it], tag: tag_siffra).save(failOnError: true)
        }
    }
}
