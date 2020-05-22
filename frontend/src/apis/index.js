import axios from 'axios';

export const accountService = axios.create( {
    baseURL: 'http://localhost:18080'
});

export const stockService = axios.create({
    baseURL: 'http://localhost:18081'
});

export const commonService = axios.create({
    baseURL: 'http://localhost:18082'
});
