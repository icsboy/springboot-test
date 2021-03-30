import React, {Component} from "react";
import WriterDocumentListComponent from "./WriterDocumentListComponent";
import CreateForm from "./CreateForm";
import EditForm from "./EditForm";
import '../../App.css';
import * as approvalApi from '../../api/approvalApi';

class WriterComponent extends Component {
    constructor(props) {
        super(props)

        this.listRef = React.createRef();

        this.state = {
            user: 1,
            message: "",
            formState: "create",
            document: {}
        }
    }

    getMember = document => {
        if (document.approverId) {
            approvalApi.getMember(document.approverId).then((response) => {
                document.approverName = response.data.name;
                this.setState({ formState: "edit", document });
            });
        }
    };

    openEditForm = document => {
        this.getMember(document);
    };

    closeEditForm = () => {
        this.setState({ formState: "create", document: {} });
    };

    reloadList = () => {
        this.listRef.current.reloadData();
    };

    renderForm = () => {
        let state = this.state.formState;

        if (state === "edit") {
            return <EditForm
                currentUserId={this.state.user}
                reloadList={this.reloadList}
                document={this.state.document}
                closeEditForm={this.closeEditForm}
                onChange={this.onChange}
            />
        } else {
            return <CreateForm
                reloadList={this.reloadList}
                creatorId={this.state.user}
            />
        }
    };

    onChange = (event) => {
        this.setState(prevState => ({
            document: {
                ...prevState.document,
                [event.target.name]: event.target.value
            }
        }));
    };

    render() {
        return (
            <div>
                <div className="split left">
                    <WriterDocumentListComponent
                        ref={this.listRef}
                        currentUserId={this.state.user}
                        openEditForm={this.openEditForm}
                    />
                </div>
                <div className="split right">
                    { this.renderForm() }
                </div>
            </div>
        )
    }
}

export default WriterComponent