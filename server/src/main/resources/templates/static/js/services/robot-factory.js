rbcRobotModule.factory('RobotPathFactory', function(){
	return {
		addRobotPath: function(){ return '/robot';},
		getRobotPath: function(id){ return '/robot/' + id;},
		getRobotsPath: function(){ return '/robot';},
		deleteRobotPath: function(id){ return '/robot/' + id;}
	};
});

rbcRobotModule.factory('RobotFactory', ['$http', '$q', 'RobotPathFactory', function($http, $q, RobotPathFactory){
	var factory = {

    robots: [],

		addRobot: function(robot){
			var deferred = $q.defer();

			$http.post(RobotPathFactory.addRobotPath(), robot).then(function(response) {
					var robot = response.data;
					factory.robots.push(robot);
					deferred.resolve(robot);
				}, function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		},

		getRobot: function(id) {
			var deferred = $q.defer();

			$http.get(RobotPathFactory.getRobotPath()).then(function(response) {
					deferred.resolve(response.data);
				}, function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		},

		getRobots: function() {
			var deferred = $q.defer();

			$http.get(RobotPathFactory.getRobotsPath()).then(function(response) {
					deferred.resolve(response.data);
				}, function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		},

		deleteRobot: function(id) {
			var deferred = $q.defer();

			$http.delete(RobotPathFactory.deleteRobotPath(id)).then(function(response) {
			    deferred.resolve(response.data);
				},function(response) {
					deferred.reject(response.data.message);
			});
			return deferred.promise;
		}
	}
	return factory;
}]);
