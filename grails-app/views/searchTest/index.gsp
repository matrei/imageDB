<html>
<body>
<h1>${searchResults.size()} träffar</h1>
<ul>
    <g:each var="result" in="${searchResults}">
        <li>${result.filename}</li>
    </g:each>
</ul>
</body>
</html>