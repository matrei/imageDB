package imagedb

class Metadata {

    ImageAsset image
    static belongsTo = [ image:ImageAsset ]

    String name
    String value

    static constraints = {
        name blank: false
        value blank: false
    }

    static mapping = {
        value type: 'text'
    }

    /*
    static searchable = {
        root false
        only = [ 'name', 'value' ]
        name boost: 0.4
        value boost: 0.5
    }
    */
}
