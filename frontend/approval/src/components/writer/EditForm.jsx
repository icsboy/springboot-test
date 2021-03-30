import React, {Component} from "react";
import '../../App.css';
import * as approvalApi from '../../api/approvalApi';

class EditForm extends Component {
    constructor(props) {
        super(props)

        this.initialState = this.state;
    }

    shouldComponentUpdate(nextProps) {
        return nextProps.document !== this.props.document;
    };

    closeEditForm = () => {
        this.props.closeEditForm();
    };

    edit = () => {
        const document = this.props.document;
        const creatorId = this.props.currentUserId;
        approvalApi.editDocument(document.id, document.content, creatorId)
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
        const { title, content, status, approverName } = this.props.document;

        return (
            <div>
                <div className="inputMargin">
                    제목: {title}
                </div>
                <div className="inputMargin">
                    결재자: {approverName}
                </div>
                <div className="inputMargin">
                    <label htmlFor="content">내용</label>
                    <input type="text" id="content" name="content" value={content} placeholder="내용을 입력하세요" onChange={this.props.onChange} />
                </div>
                <div className="inputMargin">
                    상태: { this.renderStatus(status) }
                </div>

                <button onClick={this.edit}>수정</button>
                <button onClick={this.closeEditForm}>취소</button>
            </div>
        )
    }
}

export default EditForm