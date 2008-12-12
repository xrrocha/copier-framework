function rtrim(str) {
    var lastIndex = str.length - 1
    while (lastIndex >= 0) {
        var chr = str.charAt(lastIndex)
        if (chr != ' ' && chr != '\t' && chr != '\r' && chr != '\n') {
            break
        }
        lastIndex--
    }
    return str.substring(0, lastIndex + 1)
}
