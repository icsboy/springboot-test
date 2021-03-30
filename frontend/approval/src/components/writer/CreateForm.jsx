import React, {Component} from "react";
import '../../App.css';
import * as approvalApi from '../../api/approvalApi';

class CreateForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            title: "",
            content: "",
            members: [],
            approverId: 2,
        }

        this.initialState = this.state;
    }

    componentDidMount() {
        this.getMembers();
    };

    getMembers = () => {
        approvalApi.getMembers()
            .then((response) => {
                this.setState({ members: response.data });
                this.initialState = this.state;
            });
    };

    onChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    };

    create = () => {
        const { title, content, approverId } = this.state;
        const { creatorId } = this.props;
        approvalApi.createDocument(title, content, approverId, creatorId)
            .then((response) => {
                this.props.reloadList();
            }, (error) => {
                console.log(error);
            });

        this.setState(this.initialState);
    };

    render() {
        return (
            <div>
                <div className="inputMargin">
                    <label htmlFor="title">제목</label>
                    <input type="text" id="title" name="title" placeholder="제목을 입력하세요" value={this.state.title} onChange={this.onChange} />
                </div>
                <div className="inputMargin">
                    <label htmlFor="content">내용</label>
                    <input type="text" id="content" name="content" placeholder="내용을 입력하세요" value={this.state.content} onChange={this.onChange} />
                </div>
                <div className="inputMargin">
                    <label htmlFor="members">결재자</label>
                    <select name="approverId" id="approverId" onChange={this.onChange} >
                        { this.state.members.map((member, index) => (
                            <option key={index} value={member.id}>
                                {member.name}
                            </option>
                        ))}
                    </select>
                </div>

                <button onClick={this.create}>추가</button>
            </div>
        )
    }
}

export default CreateForm