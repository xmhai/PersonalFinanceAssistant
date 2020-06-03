import axios from 'axios';

export const commonService = axios.create({ baseURL: 'http://localhost:10080' });
export const accountService = axios.create({ baseURL: 'http://localhost:10081' });
export const stockService = axios.create({ baseURL: 'http://localhost:10082' });
export const jobService = axios.create({ baseURL: 'http://localhost:10083' });

const setupAxiosInterceptors = (store) => {
    const { dispatch } = store;
    const onRequestSuccess = config => {
        // show loading spinner
        dispatch({ type: "SHOW_LOADER", data: true });
        return config;
    };
    const onRequestFail = error => {
        // hide loading spinner
        dispatch({ type: "HIDE_LOADER", data: false });
        return Promise.reject(error);
    };
    accountService.interceptors.request.use(onRequestSuccess, onRequestFail);
    stockService.interceptors.request.use(onRequestSuccess, onRequestFail);
    commonService.interceptors.request.use(onRequestSuccess, onRequestFail);
    jobService.interceptors.request.use(onRequestSuccess, onRequestFail);

    const onResponseSuccess = response => {
        // hide loading spinner
        dispatch({ type: "HIDE_LOADER", data: false });
        return response;
    };
    const onResponseFail = error => {
        // show error message
        if (!error.response) {
            // network error
            alert(error.message);
        } else {
            // http status code
            //const code = error.response.status
            // response data
            //const response = error.response.data
        }

        // hide loading spinner
        dispatch({ type: "HIDE_LOADER", data: false });

        return Promise.reject(error);
    };
    accountService.interceptors.response.use(onResponseSuccess, onResponseFail);
    stockService.interceptors.response.use(onResponseSuccess, onResponseFail);
    commonService.interceptors.response.use(onResponseSuccess, onResponseFail);
    jobService.interceptors.response.use(onResponseSuccess, onResponseFail);
};

export default setupAxiosInterceptors;
