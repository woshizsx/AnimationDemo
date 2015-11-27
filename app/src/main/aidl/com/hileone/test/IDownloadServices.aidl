// IDownloadServices.aidl
package com.hileone.test;

import com.hileone.test.IDownloadInfoDispatch;
// Declare any non-default types here with import statements

interface IDownloadServices {

    void download(String path);

    void registListener(IDownloadInfoDispatch listener);

    void unregistListener(IDownloadInfoDispatch listener);
}
