import axios from "axios";

const baseUrl = 'http://localhost:8080/api';

export const createDocument = (title, content, approverId, creatorId) => {
    return axios.post(`${baseUrl}/document/new`, {title, content, approverId, creatorId});
};

export const getCreatorDocuments = creatorId => {
    return axios.get(`${baseUrl}/documents/creator/${creatorId}`);
};

export const getApproverDocuments = approverId => {
    return axios.get(`${baseUrl}/documents/approver/${approverId}`);
};

export const editDocument = (id, content, creatorId) => {
    return axios.post(`${baseUrl}/edit/${id}`, { content, creatorId });
};

export const updateDocumentState = (id, status, approverId) => {
    return axios.post(`${baseUrl}/updateDocumentState/${id}`, { status, approverId });
};

export const deleteDocument = (id, creatorId) => {
    return axios.delete(`${baseUrl}/document/${id}`,{
        data: { creatorId }
    });
};

export const getMember = approverId => {
    return axios.get(`${baseUrl}/member/${approverId}`);
};

export const getMembers = () => {
    return axios.get(`${baseUrl}/members/approvers`);
};



