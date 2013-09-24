# Build Monitor

This project is a self-hostable application that provides a dashboard for build statuses.  In order to facilitate self-hosting, the application is designed to work in [Cloud Foundry][].


## Requirements

### PostgreSQL Database
The application stores its internal data model in a [PostgreSQL][] database.  This database needs to be provisioned before the application is started, but the application manages the creation and modification of the schemas itself so no other setup is needed.  Since the application is designed to work in a PaaS environment, it is highly recommended that you use a database-as-a-service available from your PaaS provider.

### Java, Maven
The application is written and Java and packaged as a self executable JAR file.  This enables it to run in anywhere that Java is available.  Building the application (required for deployment) requires [Maven][].


## Deployment
_The following instructions assume that you have [created an account][cloud-foundry-account] and [installed the `cf` command line tool][]._

In order to automate the deployment process as much as possible, the project contains a Cloud Foundry [manifest][].  To deploy run the following commands:

```bash
mvn -Dmaven.test.skip=true package
cf push
```


## Developing
The project is set up as a Maven project and doesn't have any special requirements beyond that.


## License
The project is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
[Cloud Foundry]: http://run.pivotal.io
[cloud-foundry-account]: http://docs.cloudfoundry.com/docs/dotcom/getting-started.html#signup
[installed the `cf` command line tool]: http://docs.cloudfoundry.com/docs/dotcom/getting-started.html#install-cf
[manifest]: manifest.yml
[Maven]: http://maven.apache.org
[PostgreSQL]: http://www.postgresql.org
