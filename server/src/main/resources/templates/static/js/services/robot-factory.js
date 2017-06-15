rbcRobotModule.factory('RobotPathFactory', function(){
	return {
		addRobotPath: function(){ return '/__service/robot';},
		getRobotPath: function(id){ return '/__service/robot/' + id;},
		getRobotsPath: function(){ return '/__service/robot';},
		deleteRobotPath: function(id){ return '/__service/robot/' + id;}
	};
});

rbcRobotModule.factory('RobotFactory', ['$http', '$q', 'RobotPathFactory', function($http, $q, RobotPathFactory){
	var factory = {

		addRobot: function(robot){
			var deferred = $q.defer();

			$http.post(RobotPathFactory.addRobotPath(), robot).then(function(response) {
					deferred.resolve(response.data);
				}, function(error) {
					deferred.reject(error);
			});
			return deferred.promise;
		},

		getRobot: function(id) {
			var deferred = $q.defer();

			$http.get(RobotPathFactory.getRobotPath(id)).then(function(response) {
					deferred.resolve(response.data);
				}, function(error) {
					deferred.reject(error);
			});
			return deferred.promise;
		},

		getRobots: function() {
			var deferred = $q.defer();

			$http.get(RobotPathFactory.getRobotsPath()).then(function(response) {
					deferred.resolve(response.data);
				}, function(error) {
					deferred.reject(error);
			});
			return deferred.promise;
		},

		deleteRobot: function(id) {
			var deferred = $q.defer();

			$http.delete(RobotPathFactory.deleteRobotPath(id)).then(function(response) {
			    deferred.resolve(response.data);
				},function(error) {
					deferred.reject(error);
			});
			return deferred.promise;
		}
	}
	return factory;
}]);
