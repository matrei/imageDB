package imagedb

class SearchTestController {

    def elasticSearchService

    def index() {
        def tags = Tag.search("${params.query}").searchResults
        def images = tags*.images.flatten().unique()
        [searchResults: images]
    }
}
