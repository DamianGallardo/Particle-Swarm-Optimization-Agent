import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;



@SuppressWarnings("unchecked")
public class PSO_Agent extends Agent {


    protected void setup() {
        System.out.println("PSO: Agent "+getLocalName()+" waiting for CFP!");
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP) );

        addBehaviour(new ContractNetResponder(this, template) {
            @Override
            protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
                System.out.println("PSO: Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());



                String proposal = evaluateAction();
                // We provide a proposal
                System.out.println("PSO: Agent "+getLocalName()+": Proposing "+proposal);
                ACLMessage propose = cfp.createReply();
                propose.setPerformative(ACLMessage.PROPOSE);
                propose.setContent(String.valueOf(proposal));
                return propose;
            }

            @Override
            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
                System.out.println("PSO: Agent "+getLocalName()+": Proposal accepted!");
                System.out.println("Calculanding iterations...");

                performAction();

                System.out.println("PSO: Agent "+getLocalName()+": Action successfully performed!");
                ACLMessage inform = accept.createReply();
                inform.setPerformative(ACLMessage.INFORM);
                //inform.setContent(String.valueOf(route));
                return inform;

            }

            protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
                System.out.println("PSO: Agent "+getLocalName()+": Proposal rejected!!!");
            }
        } );
    }

    private String evaluateAction() {
        // Simulate an evaluation by generating a random number
        String str = "PSO METHOD";
        return str;
    }

    private void performAction() {
            new PSOProcess().execute();
    }
}