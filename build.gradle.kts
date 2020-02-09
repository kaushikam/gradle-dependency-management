plugins {
	java
}

repositories {
	mavenCentral()
	jcenter()
}

// If you want to see the dependencies of a configuration or all configurations
// gradle dependencies --configuration <configuration name>
dependencies {
	modules {
		module("com.google.collections:google-collections") {
			replacedBy("com.google.guava:guava", "google collections is deprecated")
		}
	}
	implementation("com.google.collections:google-collections:1.0")
	implementation("ch.qos.logback:logback-classic:1.2.1")
	implementation("org.slf4j:slf4j-api") {
		// logback classic 1.2.1 needs slf4j-api 1.7.22 at least
		// to overcome that we need to use isForce = true
		// the version closure can be avoided and version can be used with implementation
		// function
		isForce = true
		version {
			strictly("1.7.19")
		}
	}

	// Here we are not specifying the version. Version is specified in the constrains
	// closure. There we can specify the reason for the using that specific version
	// commons-codec is transient dependency. There we are using the version 1.11 instead
	// of the default 1.9. One thing to be noted is that we can upgrade the version,
	// but cannot downgrade the version from the required version. If you want to downgrade
	// you have to use the 'isForce' , that also cannot be used in the 'constraints' block
	implementation("org.apache.httpcomponents:httpclient")
	// Here we are not mentioning version, but specifying it in the constraints block
	implementation("org.springframework:spring-core")
	constraints {
		implementation("org.apache.httpcomponents:httpclient:4.5.3") {
			because("Previous version have bug impacting this application")
		}
		implementation("commons-codec:commons-codec:1.11") {
			because("version 1.9 pulled from httpclient has bug affecting this application")
		}

		add("implementation", "org.springframework:spring-core") {
			version {
				require("4.2.9.RELEASE")
				reject("5.2.3.RELEASE")
			}
		}
	}

	// Rich versioning
	implementation("org.apache.commons:commons-lang3") {
		// Means version must be between 3.3.1 and 3.8
		// If 3.4 is present that's nice, we will take it
		version {
			strictly("[3.3.1,3.8[")
			prefer("3.4")
		}
	}

	implementation("com.google.inject:guice") {
		// Means version must be between 4.0 and 4.2.1
		// As 'require' has more preference over 'prefer'
		// 4.2.0 will be used
		version {
			strictly("[4.0,4.2.1[")
			require("4.2.0")
			prefer("4.1.0")
		}
	}
}