import React, {Component} from "react";
import * as approvalApi from '../../api/approvalApi';

class ListComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            documents: []
        }
    }

    componentDidMount() {
        this.getDocuments();
    };

    getDocuments = () => {
        const approverId = this.props.currentUserId;
        approvalApi.getApproverDocuments(approverId)
            .then((response) => {
                this.setState({ documents: response.data })
            });
    };

    edit = document => {
        this.props.openApproveForm(document);
    };

    renderStatus = status => {
        if (status === 'request') {
            return "요청";
        } else if (status === 'approved') {
            return "승인";
        } else if (status === 'denied') {
            return "거절";
        }

        return "알수 없음";
    };

    renderListItem = documents => {
        return documents.map((document, index) => (
            <tr key={index}>
                <td>{ document.title }</td>
                <td>{ document.content }</td>
                <td>{ this.renderStatus(document.status) }</td>
                { document.status === "request" &&
                    <td><button onClick={() => this.edit(document)}>수정</button></td>
                }
            </tr>
        ));
    };

    reloadData = () => {
        this.getDocuments();
    };

    render() {
        let documents = this.state.documents;

        return (
            <div>
               <div><a href="/">메인으로 이동</a></div>
               <table>
                    <thead>
                        <tr>
                            <th>제목</th>
                            <th>내용</th>
                            <th>상태</th>
                            <th>수정</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.renderListItem(documents)
                        }
                    </tbody>
               </table>
            </div>
        )
    }
}

export default ListComponent