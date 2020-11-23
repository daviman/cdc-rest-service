#### CDC Demo Web Service for Openshift Demo
####

#### Build

```bash
./bin_sh/oc-new-app.sh
```

#### Watch the build log file ...

```bash
oc logs -f bc/cdc-rest-service
```

#### Expose the service to make accessible to public

```bash
oc expose svc/cdc-rest-service
```

#### Cleanup

```bash
./bin_sh/clean_up.sh
```
