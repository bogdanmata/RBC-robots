var rbcRobotModule = angular.module('rbcRobotApp', ['ngRoute']);

rbcRobotModule.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/robot', {
		templateUrl: 'partials/robots.html',
		controller: 'RobotListController',
		resolve: { robots: getRobots }
	})
	.when('/robot/create', {
		templateUrl: 'partials/robot-create.html',
		controller: 'RobotCreateController'
	})
	.when('/robot/:robotId', {
		templateUrl: 'partials/robot.html',
		controller: 'RobotController',
		resolve: { robot: getRobot }
	})
  .when('/login', {
    templateUrl: 'partials/login/login.html',
		controller: 'LoginController',
  })
	.otherwise({redirectTo: '/'});
}]);

function getRobots(RobotFactory){
	return RobotFactory.getRobots();
}

function getRobot($route, RobotFactory){
	return RobotFactory.getRobot($route.current.params.robotId);
}
