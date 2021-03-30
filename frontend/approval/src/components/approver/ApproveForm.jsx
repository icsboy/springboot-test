import React, {Component} from "react";
import '../../App.css';
import * as approvalApi from '../../api/approvalApi';

class ApproveForm extends Component {
    constructor(props) {
        super(props)
    }

    shouldComponentUpdate(nextProps) {
        return nextProps.document !== this.props.document;
    };

    loadData = document => {
        this.setState({ document: document });
    }

    closeEditForm = () => {
        this.props.closeEditForm();
    };

    updateDocumentState = (id, status) => {
        const approverId = this.props.currentUserId;
        console.log(id);
        approvalApi.updateDocumentState(id, status, approverId)
            .then((response) => {
                this.setState({
                    content: response.data.content,
                    status: response.data.status
                });
                this.props.reloadList();
            }, (error) => {
                console.log(error);
            });
    }

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

    render() {
        const { id, title, content, status, creatorId, creatorName } = this.props.document;

        return (
            <div>
                <div className="inputMargin">
                    제목: {title}
                </div>
                <div className="inputMargin">
                    상신자: {creatorName}
                </div>
                <div className="inputMargin">
                    내용: {content}
                </div>
                <div className="inputMargin">
                    상태: { this.renderStatus(status) }
                </div>

                <button onClick={() => this.updateDocumentState(id, 'approved')}>승인</button>
                <button onClick={() => this.updateDocumentState(id, 'denied')}>거절</button>
            </div>
        )
    }
}

export default ApproveForm