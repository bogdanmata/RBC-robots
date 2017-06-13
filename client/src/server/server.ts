const http = require('http');
const url = require('url');
const httpProxy = require('http-proxy');
const fs = require('fs');

const target = 'http://localhost:9999';
const proxy = httpProxy.createProxyServer();

proxy.on('error', function(e) {
  console.log('proxy error: ' + e.message);
});

const server = http.createServer(function(req, res) {
  if(/^\/__service\//i.test(req.url)) {

  } else {
    var originalFile = url.parse(req.url).pathname;
    var regexString = /(^\/js\/)|(^\/css\/)/g;
    regexString.exec(originalFile);

    var fileName = (regexString.lastIndex == 0) || ('\/' == originalFile) ? './../app/index.html' : './../app' + originalFile;
    console.log('Requested resource: \"' + originalFile + '\" -> \"' + fileName + '\"');
    fs.readFile(fileName, 'utf8', function(err, data) {
      if(err){
        res.writeHead(404, {'Content-Type': 'text/plain'});
        res.end(err.message);
      } else {
        res.writeHead(200, {'Content-Type': 'text/html'});
        res.end(data);
      }
    });
  }
});

server.listen(3000);
console.log('server listening on port 3000...')
