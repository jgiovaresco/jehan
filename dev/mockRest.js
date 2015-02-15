var express = require('express');
var app = express();

var instances = [
    {
        "name": "Jenkins1",
        "secure": true,
        "url": "http://localhost:8080/"
    },
    {
        "name": "Jenkins2",
        "secure": true,
        "url": "http://localhost:8080/"
    },
    {
        "name": "Jenkins3",
        "secure": true,
        "url": "http://localhost:8080/"
    },
    {
        "name": "Jenkins4",
        "secure": true,
        "url": "http://localhost:8080/"
    }
];

var jobs = {
    Jenkins1 : [{"name":"jobs11","url":"","color":"blue"},{"name":"jobs12","url":"","color":"blue"}],
    Jenkins2 : [{"name":"jobs21","url":"","color":"blue"},{"name":"jobs22","url":"","color":"yellow"}],
    Jenkins3 : [{"name":"jobs31","url":"","color":"red"},{"name":"jobs32","url":"","color":"yellow"}],
    Jenkins4 : [{"name":"jobs41","url":"","color":"red"},{"name":"jobs42","url":"","color":"blue"}]
};

console.log("Instance : ", instances);
console.log("Jobs : ", jobs);

app.get('/rest/Instances', function(req, res){
    res.json(instances);
});
app.get('/rest/Jobs/:instances', function(req, res) {
	res.json(jobs[req.params.instances]);
//	setTimeout(function() {
//            res.json(jobs[req.params.instances]);
//        }, 3000);
});

app.listen(8280);