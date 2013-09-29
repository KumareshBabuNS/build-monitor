/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*global angular:false*/

angular.module('management', ['ng', 'links', 'underscore'])
    .controller('ProjectsController', ['$scope', '$http', '_', function ($scope, $http, _) {
        'use strict';

        $http.get('/projects').success(function (projects) {
            $scope.projects = projects;
        });

        $scope.create = function (newProject) {
            $http.post('/projects', newProject).success(function (data, status, headers) {
                var location = headers('Location');

                $http.get(location).success(function (project) {
                    $scope.projects.push(project);
                    $scope.projects = _.sortBy($scope.projects, 'key');
                });
            });
        };

    }])

    .controller('ProjectController', ['$scope', '$http', '_', 'links', function ($scope, $http, _, links) {
        'use strict';

        $scope.delete = function () {
            $http.delete(links.getLink($scope.project, 'self')).success(function () {
                _.remove($scope.projects, $scope.project);
            });
        };

        $scope.webhookUri = links.getLink($scope.project, 'webhook');

    }]);