var rbcRobotModule = angular.module('rbcRobotApp', [ 'ngRoute' ]);

rbcRobotModule.config([ '$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {

  $locationProvider.html5Mode(true);

  $routeProvider.when('/robot', {
    templateUrl : 'partials/robot-list.html',
    controller : 'RobotListController',
    resolve : {
      robots : getRobots
    }
  }).when('/robot/create', {
    templateUrl : 'partials/robot-create.html',
    controller : 'RobotCreateController'
  }).when('/robot/:robotId', {
    templateUrl : 'partials/robot-view.html',
    controller : 'RobotController',
    resolve : {
      robot : getRobot
    }
  }).when('/login', {
    templateUrl : 'partials/login/login.html',
    controller : 'LoginController',
  }).otherwise({
    redirectTo : '/'
  });
} ]);

function getRobots(RobotFactory) {
  return RobotFactory.getRobots();
}

function getRobot($route, RobotFactory) {
  return RobotFactory.getRobot($route.current.params.robotId);
}
