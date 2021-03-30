import React, {Component} from "react";
import ApproverDocumentListComponent from "./ApproverDocumentListComponent";
import ApproveForm from "./ApproveForm";
import '../../App.css';
import * as approvalApi from '../../api/approvalApi';

class ApproverComponent extends Component {
    constructor(props) {
        super(props)

        this.listRef = React.createRef();

        this.state = {
            user: 2,
            message: "",
            document: {},
        }
    }

    getMember = document => {
        if (document.creatorId) {
            approvalApi.getMember(document.creatorId).then((response) => {
                document.creatorName = response.data.name;
                this.setState({ document });
            });
        }
    };

    openApproveForm = document => {
        this.getMember(document);
    };

    reloadList = () => {
        this.setState({ document: {} });
        this.listRef.current.reloadData();
    };
    render() {
        return (
            <div>
                <div className="split left">
                    <ApproverDocumentListComponent
                        ref={this.listRef}
                        currentUserId={this.state.user}
                        openApproveForm={this.openApproveForm}
                    />
                </div>
                <div className="split right">
                    <ApproveForm
                        reloadList={this.reloadList}
                        currentUserId={this.state.user}
                        document={this.state.document}
                        creatorName={this.state.creatorName}
                    />
                </div>
            </div>
        )
    }
}

export default ApproverComponent