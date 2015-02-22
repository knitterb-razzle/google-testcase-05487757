# Test Case for Google Support Case #05487757
This is a test case project for Google support case #05487757.  This is intended to demonstrate an issue I'm having with AppEngine Datatore and JDO.  This will demonstrate that while the data appears to be stored, it is not persisted in the development environment.  While I may have a configuration or code improperly implemented, this will help assist Google in resolving my case.  Look for updates at the bottom of this readme when I receive a correction.
### Summary
When I update data for an Entity the JDO layer appears to see the change (sometimes), but the Datastore Admin (/_ah/admin) will not.  Furthermore, when I stop and restart the dev_appserver the data changes are lost.
### Steps To Reproduce
1. Start dev_appserver
2. Go to the API Explorer (/_ah/api/explorer)
3. Go to the `testendpoint.listTestObjects` endpoint and execute (note no results)
4. Go to the `testendpoint.addTestObject` endpoint and add two objects **only** providing a `stringField` element (make sure they are different text)
5. Go to the Datastore Admin and note these two objects exist (might take a few seconds - HRD)
6. Go to the `testendpoint.listTestObjects` endpoint and execute and note these two objects exist (might take a few seconds - HRD)
7. Select one of the keys from one of the objects (example: `aglub19hcHBfaWRyFwsSClRlc3RPYmplY3QYgICAgICAgAkM`)
8. Go to the `testendpoint.updateTestObject` endpoint, enter the key set the `stringField` to a new string value, execute
9. Go to the `testendpoint.listTestObjects` endpoint and execute and note the change to the `stringField` (might take a few seconds - HRD) made in step 8
10. Go to the Datastore Admin and note these two objects exist but without the updated field (give it a few seconds to a few minutes, they will not change - HRD)  **This is the first place an issue arises.  The Datastore Admin (Step 10) is inconsistent with what is shown within my code (Step 9).**
11. Stop dev_appserver
12. Go to the `testendpoint.listTestObjects` endpoint and execute and note that the data changed in step 8 is now lost.  **This is the second place an issue arises.  Data is not persisted to disk as it should be.**

###Notes
#####Configuration: Persistence (dev_appserver)
I added the `-Ddatastore.store_delay=1000` flag to the VM Aruguments.  When when the following is displayed, it appears that the data truly is **not** persisted properl

    INFO: Time to persist datastore: 4 ms

I also checked the md5 hash of the local_db.bin file before making the testObject stringField update, and after; the md5 hash does indeed change, but it doesn't appear to actually persist the data changes,

#####Configuration: HRD (dev_appserver)
I have tried to modify the HRD settings, and also the `-Ddatastore.default_high_rep_job_policy_unapplied_job_pct=n` flag to see if this makes a difference.  I've tried values of 0, 50 and 100 (just to make sure it wasn't inversely documented).  This resulted in no change in behavior.

#####Additional Objects
Adding new testObject do persist, but updates to them do not.  This doesn't seem to affect new data, just changed data.

